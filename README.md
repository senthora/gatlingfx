# GatlingFX

GatlingFx is a lightweight toolkit built on top of Gatling 
for writing reusable, readable, and maintainable simulations.

## Why do I need this?

Gatling is a powerful execution engine, but writing larger simulations with
directly provided DSL can become repetitive and difficult to maintain over time.

As simulations become more infrastructure-oriented, tests started accumulating 
repeated protocol configuration, backend setup, reusable checks, and shared simulation behavior.

GatlingFx provides a cleaner and more structured way to organize 
those simulations while still keeping the flexibility of Gatling underneath.
