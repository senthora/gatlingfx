set shell := ["bash", "-eu", "-o", "pipefail", "-c"]

import 'just/gradle.just'
import 'just/docker.just'
import 'just/gatling.just'

# List available recipes
default:
    @just --list
