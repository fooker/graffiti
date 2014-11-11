graph(id: 'opennms.manager.uptime',
      title: 'OpenNMS Manager Uptime') {
    uptime = source(file: '{rrd1}',
                    track: 'onmsUptime')

    group() {
        track(label: 'Uptime', { uptime / (1000 * 60 * 60 * 24) })
    }
}

graph(id: 'opennms.queued.updates',
      title: 'OpenNMS QueueD Updates') {
    completed = source(file: '{rrd1}',
                       track: 'ONMSQueUpdates')

    group() {
        track(label: 'Completed', completed)
    }
}

graph(id: 'opennms.queued.pending',
      title: 'OpenNMS QueueD Pending Operations') {
    pending = source(file: '{rrd2}',
                     track: 'ONMSQueOpsPend')

    group() {
        track(label: 'Pending', pending)
    }
}

