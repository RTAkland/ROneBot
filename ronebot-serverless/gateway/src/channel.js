export class ChannelDO {
    constructor(state, env) {
        this.state = state;
        this.env = env;

        // clientId -> WebSocket
        this.clients = new Map();
    }

    async fetch(request) {
        const url = new URL(request.url);

        if (request.headers.get("Upgrade") === "websocket") {
            const channel = url.searchParams.get("channel");
            const clientId = url.searchParams.get("clientId");

            if (!channel || !clientId) {
                return new Response("Missing channel or clientId", { status: 400 });
            }

            const pair = new WebSocketPair();
            const [client, server] = Object.values(pair);

            server.accept();
            this.clients.set(clientId, server);

            server.addEventListener("message", async (event) => {
                // 心跳
                if (event.data === "ping") {
                    server.send("pong");
                    return;
                }

                try {
                    await fetch(this.env.BACKEND_INGRESS_URL, {
                        method: "POST",
                        headers: {
                            "Content-Type": "application/json",
                            "X-Channel": channel,
                            "X-Client-Id": clientId,
                        },
                        body: event.data,
                    });
                } catch (err) {
                    console.error("forward to backend failed", err);
                }
            });

            server.addEventListener("close", () => {
                this.clients.delete(clientId);
            });

            server.addEventListener("error", () => {
                this.clients.delete(clientId);
            });

            return new Response(null, {
                status: 101,
                webSocket: client,
            });
        }

        if (request.method === "POST") {
            const target = request.headers.get("X-Target");
            const payload = await request.text();

            let delivered = 0;

            if (target) {
                const ws = this.clients.get(target);
                if (ws) {
                    ws.send(payload);
                    delivered = 1;
                }
            } else {
                for (const ws of this.clients.values()) {
                    ws.send(payload);
                    delivered++;
                }
            }

            return new Response(
                JSON.stringify({
                    ok: true,
                    delivered,
                    online: this.clients.size,
                }),
                { headers: { "Content-Type": "application/json" } }
            );
        }

        return new Response("Not Found", { status: 404 });
    }
}
