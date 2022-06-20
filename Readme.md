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
Enter '1' for normal play and '2' for Super Over
1
Enter No. of inputs
10
Enter inputs in format: 'bowl-type, bat-type, timing'
yorker straight good
6 runs, Shot!, Over the long on
bouncer pull perfect
2 runs, In the gap that will be 2 runs.
slower-ball sweep early
4 runs, A gentle shuffle and a clip, four for the taking
bouncer scoop late
1 wicket, Straight Down the Fielder's Throat, and he is out.
out-swinger flick good
0 runs, Defended, no run.
pace leglance good
3 runs, Fielder saving the boundary there, 3 runs.
yorker straight perfect
3 runs, Fielder saving the boundary there, 3 runs.
doosra long-on good
6 runs, That has taken the aerial route, and a Six!
bouncer scoop perfect
2 runs, That's a Proper Cricket Shot. A couple.
slower-ball sweep late
2 runs, In the gap that will be 2 runs.
```

### SuperOver

```
Enter '1' for normal play and '2' for Super Over
2
Bowling cards: [inswinger, yorker, off-cutter, out-swinger, pace, out-swinger]
India 11 Score:
5 runs (Target Runs: 6 )
Wickets Available: 2
Enter inputs in format: 'bat-type, timing'
straight perfect
3 runs, Dropped!, That's gonna cost them, 3 runs.
Enter inputs in format: 'bat-type, timing'
straight perfect
4 runs, A gentle shuffle and a clip, four for the taking
Australia won by 2 wicket
Australia scored: 7 runs
```
