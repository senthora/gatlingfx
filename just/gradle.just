# Build project
build:
    just gradle build

[private]
gradle +args:
    ./gradlew {{args}} --stacktrace
