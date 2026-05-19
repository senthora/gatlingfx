# Run all Gatling simulations
run-simulations:
    just gatling --all

# Run single Gatling simulation
[arg("simulation", help="simulation class")]
run-simulation simulation:
    just gatling --simulation {{simulation}}

[private]
gatling +args:
    just gradle gatlingRun {{args}}
