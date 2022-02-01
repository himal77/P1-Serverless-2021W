function main(args) {
    let name = args.name || 'stranger'
    let greeting = 'Hello ' + name + '!'
    mysleep(1000)
    return { "body": greeting }
}

function mysleep(miliseconds) {
    const date = Date.now();
    let currentDate = null;
    do {
        currentDate = Date.now();
    } while (currentDate - date < miliseconds);
}