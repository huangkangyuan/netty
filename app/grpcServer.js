//动态生成
var PROTO_FILE_PATH = 'F:/WebStormProject/grpc_demo/proto/Student.proto';
var grpc = require('grpc');
//包名必须和服务端的一致
var grpcService = grpc.load(PROTO_FILE_PATH).com.hl.netty._12grpcStreamBoth;

var server = new grpc.Server();

//#当客户端调用getRealNameByUsername的时候,服务端用getRealNameByUsername进行处理
server.addService(grpcService.StudentService.service,{
    getRealNameByUsername:getRealNameByUsername,
    getStudentsByAge:getStudentsByAge,
    getStudentsWrapperByAge:getStudentsWrapperByAge,
    byTalk:byTalk

});

server.bind('localhost:8899',grpc.ServerCredentials.createInsecure());
server.start();


function getRealNameByUsername(call,callBack){
    console.log('call:'+call.request.username);
    callBack(null,{realname:'张三'});
}

function getStudentsByAge() {
    
}

function getStudentsWrapperByAge() {
    
}

function byTalk() {
    
}
