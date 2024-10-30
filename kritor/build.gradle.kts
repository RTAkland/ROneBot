plugins {
    alias(libs.plugins.protobuf)
}

dependencies {
    protobuf(files("kritor/protos"))
    api(project(":common"))
    api(libs.grpc.stub)
    api(libs.grpc.protobuf)
    api(libs.protobuf.java.util)
    api(libs.protobuf.kotlin)
    api(libs.grpc.kotlin.stub)
    api(libs.grpc.netty)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.3"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.62.2"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("grpc")
                create("grpckt")
            }
            it.builtins {
                create("kotlin")
            }
        }
    }
}

sourceSets {
    main {
        proto {
            srcDir("kritor/kritor/protos")
        }
    }
}

tasks.jar {
    exclude("**/*.proto")
}