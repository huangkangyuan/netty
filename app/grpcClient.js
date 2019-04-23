var PROTO_FILE_PATH = 'F:/WebStormProject/grpc_demo/proto/Student.proto';
var grpc = require('grpc');

var grpcService = grpc.load(PROTO_FILE_PATH).com.hl.netty._12grpcStreamBoth;

var client = new grpcService.StudentService('localhost:8899',grpc.credentials.createInsecure());

client.getRealNameByUsername({username:"list"},function (error,responseData) {
    console.log(responseData);
});