package sh.lab.graffiti.dsl.examples

def uc(s) {
    return s.toUpperCase()
}

graph(id: 'foo',
      title: uc("FOO")) {
    o = source(file: "host.rrd",
               track: "output")

    i = source(file: "host.rrd",
               track: "input")

    x = source(file: "host.rrd",
               track: "errors",
               consolidation: AVERAGE)

    group() {
        stacked = true

        track(label: "In Bits") { i * 8 }
        track(label: "Out Bits", negate: true) { 2 * 4 * o }
        track(label: "Errors", x)
    }

    group(stacked: true) {
        track(label: "In Bits") { i * (1 / o) }
        track(label: "Out Bits") { -(o * (1 / i)) }
    }
}
