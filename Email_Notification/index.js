exports.main = function (params) {
    const sgMail = require('@sendgrid/mail')
    const API_KEY = "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"
    sgMail.setApiKey(API_KEY)

    msgTo = params.to || 'purihimal9@gmail.com';
    msgSender = "himalpuri6@gmail.com"
    msgSubject = params.subject || 'Hello from OpenWhisk';
    msgText = params.text || 'This a simple notification message' ;

    const msg = {
      to: msgTo,
      from: msgSender,
      subject: msgSubject,
      text: msgText
    }

    sgMail
      .send(msg)
      .then((response) => {
        return {"statusCode": response[0].statusCode, "header": response[0].headers, "body": params}
      })
      .catch((error) => {
        console.error(error)
        return {"msg": error, "body":params}
      })
}
/*
ref : 
For finding main: http://jamesthom.as/2016/11/npm-modules-in-openwhisk/
For conference youtube: https://www.youtube.com/watch?v=0kbFghAtvm0&t=2770s
for using sendgrid: https://www.youtube.com/watch?v=qFDgH6dHRA4&t=574s
*/