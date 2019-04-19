package com.hl.netty._8thrift;

import org.apache.thrift.TException;

public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByName(String name) throws DataException, TException {
        System.out.println("Get client param:"+name);
        Person person = new Person();
        person.setName("hky");
        person.setAge(22);
        person.setMarried(true);
        return person;
    }

    @Override
    public void savePerson(Person person) throws DataException, TException {
        System.out.println("Get client param");

        System.out.println(person.getName());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
