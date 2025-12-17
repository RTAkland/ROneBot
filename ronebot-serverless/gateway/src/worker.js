/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 20:54
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

import {ChannelDO} from "./channel.js";

export default {
    fetch(request, env, ctx) {
        const url = new URL(request.url);
        if (url.pathname === "/gateway") {
            const channel = url.searchParams.get("channel");
            const clientId = url.searchParams.get("clientId");
            if (!channel || !clientId) {
                return new Response("Missing channel or clientId", {status: 400});
            }
            const id = env.CHANNEL_DO.idFromName(channel);
            const stub = env.CHANNEL_DO.get(id);
            return stub.fetch(request);
        }
        if (url.pathname === "/push" && request.method === "POST") {
            const channel = request.headers.get("X-Channel");
            if (!channel) {
                return new Response("Missing X-Channel", {status: 400});
            }
            const id = env.CHANNEL_DO.idFromName(channel);
            const stub = env.CHANNEL_DO.get(id);
            return stub.fetch(request);
        }
        return new Response("Not Found", {status: 404});
    },
};

export {ChannelDO};
