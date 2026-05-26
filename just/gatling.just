# Run all Gatling simulations via GatlingFx
run-simulations:
    ./gradlew gatlingFx

# Run single Gatling simulation via GatlingFx
[arg("simulation", help="simulation class")]
run-simulation simulation:
    ./gradlew gatlingFx --args="--simulation {{simulation}}"
