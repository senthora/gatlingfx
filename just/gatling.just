# Run all Gatling simulations via GatlingFx
[arg("args", help="GatlingFx arguments")]
run-simulations *args:
    @./gradlew :launcher:run {{ if args != '' { '--args="' + args + '"' } else { '' } }}

# Run single Gatling simulation via GatlingFx
[arg("simulation", help="simulation class")]
[arg("args", help="GatlingFx arguments")]
run-simulation simulation *args:
    @./gradlew :launcher:run --args="--simulation {{simulation}} {{args}}"
