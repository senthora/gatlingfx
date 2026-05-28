# Run all Gatling simulations via GatlingFx
run-simulations *args:
    ./gradlew :launcher:run {{args}}

# Run single Gatling simulation via GatlingFx
[arg("simulation", help="simulation class")]
run-simulation simulation:
    ./gradlew :launcher:run --args="--simulation {{simulation}}"
