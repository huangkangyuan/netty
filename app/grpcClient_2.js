var service  = require("../static_codegen/proto/Student_grpc_pb");

var message = require("../static_codegen/proto/Student_pb");

var grpc = require("grpc");
var client = new service.StudentServiceClient("localhost:8899",grpc.credentials.createInsecure());

var request = new message.MyRequest();
request.setUsername("王五");

client.getRealNameByUsername(request,function (error,responseData) {
    console.log(responseData.getRealname());
});