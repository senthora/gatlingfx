# Run all Gatling simulations via GatlingFx
run-simulations:
    ./gradlew :launcher:run

# Run single Gatling simulation via GatlingFx
[arg("simulation", help="simulation class")]
run-simulation simulation:
    ./gradlew :launcher:run --args="--simulation {{simulation}}"
