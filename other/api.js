function main(args) {
    let name = args.name || 'stranger'
    let greeting = 'Hello ' + name + ', I am from API!'
    return {"body":  greeting}
}