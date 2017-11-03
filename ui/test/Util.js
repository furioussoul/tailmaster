/**
 * Mix properties into target object.
 */
module.exports.extend = function (to, _from) {
    for (var key in _from) {
        to[key] = _from[key]
    }
    return to
}
