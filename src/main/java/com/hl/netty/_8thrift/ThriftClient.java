package com.hl.netty._8thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.omg.SendingContext.RunTime;

public class ThriftClient {

    public static void main(String[] args) {
        TTransport tTransport = new TFramedTransport(new TSocket("localhost",8899),600);

        TProtocol protocol = new TCompactProtocol(tTransport);
        PersonService.Client client = new PersonService.Client(protocol);

        try{
            tTransport.open();
            Person person = client.getPersonByName("hky");
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.isMarried());

            System.out.println("-------------------");

            Person person2 = new Person();
            person2.setName("lsh");
            person2.setAge(24);
            person2.setMarried(true);
            client.savePerson(person2);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }finally {
            tTransport.close();
        }
    }
}
