"use strict"

var cnst = function (x) {
    return function () {
        return x
    }
};

var IND = {
    "x": 0,
    "y": 1,
    "z": 2,
    "w": 3,
    "q": 4
};

var variable = function (x) {
    return function () {
        return arguments[IND[x]]
    }
};

for (var v in IND) {
    this[v] = variable(v)
}

var calc = function (operation) {
    return function () {
        var args = arguments
        return function () {
            var local = []
            for (var i = 0; i < args.length; i++) {
                local.push(args[i].apply(null, arguments))
            }
            return operation.apply(null, local)
        }
    }
};

var add = calc(function (a, b) {
    return a + b
});

var subtract = calc(function (a, b) {
    return a - b
});

var multiply = calc(function (a, b) {
    return a * b
})

var divide = calc(function (a, b) {
    return a / b
})

var negate = calc(function (a) {
    return -a
})

var pow = function (y) {
    return calc(function (x) {
        return Math.pow(x, y)
    })
}

var cube = pow(3)

var cuberoot = pow(1 / 3)

var min3 = calc(function () {
    return Math.min.apply(null, arguments)
})

var max5 = calc(function () {
    return Math.max.apply(null, arguments)
})

var CNST = {
    "pi": Math.PI,
    "e": Math.E
}

for (var name in CNST) {
    this[name] = cnst(CNST[name])
}

var parse = function (pattern) {
    var OPERATIONS = {
        "+": add,
        "-": subtract,
        "*": multiply,
        "/": divide,
        "negate": negate,
        "cube": cube,
        "cuberoot": cuberoot,
        "min3": min3,
        "max5": max5
    }
    var ARGS_CNT = {
        "+": 2,
        "-": 2,
        "*": 2,
        "/": 2,
        "negate": 1,
        "cube": 1,
        "cuberoot": 1,
        "min3": 3,
        "max5": 5
    }
    var elements = pattern.split(" ").filter(function (x) {
        return x.length > 0;
    })
    var data = []
    for (var i = 0; i < elements.length; i++) {
        if (elements[i] in CNST) {
            data.push(cnst(CNST[elements[i]]))
        } else if (elements[i] in IND) {
            data.push(variable(elements[i]))
        } else if (elements[i] in OPERATIONS) {
            var args = []
            for (var k = 0; k < ARGS_CNT[elements[i]]; k++) {
                args.push(data.pop())
            }
            args.reverse()
            data.push(OPERATIONS[elements[i]].apply(null, args))
        } else {
            data.push(cnst(parseInt(elements[i])))
        }
    }
    return data.pop()
}