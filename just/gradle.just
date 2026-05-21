# Execute Gradle task in core module
[arg("args", help="gradle arguments")]
gradle-core +args:
    just gradle core {{args}}

# Execute Gradle task in proxy module
[arg("args", help="gradle arguments")]
gradle-proxy +args:
    just gradle proxy {{args}}

# Execute Gradle task in wiremock module
[arg("args", help="gradle arguments")]
gradle-wiremock +args:
    just gradle wiremock {{args}}

[private]
gradle module +args:
    ./gradlew :{{module}}:{{args}} --stacktrace
