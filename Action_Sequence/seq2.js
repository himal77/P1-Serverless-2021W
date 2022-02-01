var openwhisk = require('openwhisk');

function main(params) {
	options={apihost: params.apihost, api_key:params.api_key};

	var ow = openwhisk(options);
	return ow.actions.invoke({name: "seq1", blocking: true, result: true, params: params});
}
