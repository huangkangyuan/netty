var service  = require("../static_codegen/proto/Student_grpc_pb");

var message = require("../static_codegen/proto/Student_pb");
var grpc = require("grpc");

var server = new grpc.Server();

//#当客户端调用getRealNameByUsername的时候,服务端用getRealNameByUsername进行处理
server.addService(service.StudentServiceService,{
    getRealNameByUsername:getRealNameByUsername,
    getStudentsByAge:getStudentsByAge,
    getStudentsWrapperByAge:getStudentsWrapperByAge,
    byTalk:byTalk

});

server.bind('localhost:8899',grpc.ServerCredentials.createInsecure());
server.start();


function getRealNameByUsername(call,callBack){
    console.log('call:'+call.request.getUsername());

    var myResponse = new message.MyResponse();
    myResponse.setRealname("赵六");
    callBack(null,myResponse);
}

function getStudentsByAge() {
    
}

function getStudentsWrapperByAge() {
    
}

function byTalk() {
    
}
