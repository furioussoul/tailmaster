class EventCenter {
  subs = {}

  trigger(eventName, param) {
    if (!this.subs[eventName]) {
      return void console.error('no event:' + eventName + " bind")
    }
    this.subs[eventName](param)
  }

  bind(eventName, cb) {
    this.subs[eventName] = cb
  }
}

export default new EventCenter()
