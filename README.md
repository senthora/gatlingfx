# GatlingFX

GatlingFx is a lightweight toolkit built on top of Gatling 
for writing reusable, readable, and maintainable simulations.

## Why do I need this?

Gatling is a powerful execution engine, but writing larger simulations 
directly provided DSL can become repetitive and difficult to maintain over time.

As simulations become more infrastructure-oriented, tests started accumulating 
repeated protocol configuration, backend setup, reusable checks, and shared simulation behavior.

GatlingFx provides a cleaner and more structured way to organize 
those simulations while still keeping the flexibility of Gatling underneath.

## Motivation

GatlingFx started as a personal abstraction layer over Gatling after writing larger simulations
directly with the Gatling DSL became repetitive and difficult to maintain.

The project is not trying to replace Gatling, but to provide a cleaner and more structured way
to organize simulations, reusable checks, backend setup, protocol configuration, and shared behavior.
Gatling remains the execution engine, while GatlingFx focuses on readability and maintainability around it.

Most current simulations are infrastructure-oriented and focus on nginx behavior, proxy chains,
trusted forwarding headers, and topology-aware testing. However, those are current use cases rather
than the core identity of the project. GatlingFx is intended to remain generic enough to support
different styles of simulations over time.

The framework intentionally stays lightweight and opinionated. Readable simulations, explicit behavior,
and practical abstractions are preferred over deep abstraction layers or highly dynamic configuration systems.

Ultimately, GatlingFx exists to provide a reusable structure for writing simulations that are easier
to read, organize, and evolve across different projects.
