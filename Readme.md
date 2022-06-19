# CricSummit

## Design

Given an input of bowlType, batType and timing, we calculate a `multiple` based on config. This multiple is used to
determine the highest probabilistic (weighted) score set from which a `score` is again selected randomly.

With this design any outcome can be returned, but is designed such a way that _most_ of the time expected outcome (based
on `config.yaml`) is returned.

So it is possible that even for the best shot and best timing, it can result in a wicket, but most of the time it will
be a boundary. And inversely, worst shot with worst timing can result into a boundary, but most o the time will be a
wicket.

### Comments

Comments are randomly selected based on the outcome and are stored in `commentary-config.yaml`

## Testing

To test this probabilistic system design, We run the input set multiple times, say 10000, and calculate the expected
result counts and assert with expected probability.

### Example Run

```
8 //No of inputs
yorker straight good
4 runs, A gentle shuffle and a clip, four for the taking
bouncer pull perfect
5 runs, Huge wide, keeper misses and 4 byes
slower-ball sweep early
1 runs, Edged and taken.
bouncer scoop late
0 runs, In the block hole, no run.
out-swinger flick good
4 runs, A misfield and a boundary
pace leglance good
1 wicket, Straight Down the Fielder's Throat, and he is out.
yorker straight perfect
6 runs, SIX runs, Elegant hit
doosra long-on good
0 runs, Defended, no run.
```
