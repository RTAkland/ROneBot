plugins {
    alias(libs.plugins.protobuf)
}

dependencies {
    api(libs.grpc.protobuf)
    api(libs.grpc.kotlin.stub)
    api(libs.grpc.netty.shaded)
    api(libs.protobuf.kotlin)
    api(project(":common"))
    protobuf(files("kritor/protos"))
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.21.12"
    }
    plugins {
        create("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.68.0"
        }
        create("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.4.1:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                create("grpc")
                create("grpckt")
            }
        }
    }
}