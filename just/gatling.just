# Run all GatlingFx simulations
[arg("args", help="GatlingFx arguments")]
run-simulations *args:
    @./gradlew gatlingFxRun {{ if args != '' { '--args="' + args + '"' } else { '' } }}

# Run single GatlingFx simulation
[arg("module", pattern="core|proxy|wiremock", help="project module")]
[arg("simulation", help="simulation class")]
[arg("args", help="GatlingFx arguments")]
run-simulation module simulation *args:
    @./gradlew :{{module}}:gatlingFxRun --args="--simulation {{simulation}} {{args}}"
