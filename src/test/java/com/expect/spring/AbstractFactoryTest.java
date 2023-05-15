package com.expect.spring;


class AbstractProductA {
}

class AbstractProductB {
}

class ProductA1 extends AbstractProductA {
}

class ProductA2 extends AbstractProductA {
}

class ProductB1 extends AbstractProductB {
}

class ProductB2 extends AbstractProductB {
}

abstract class AbstractFactory {
    abstract AbstractProductA createProductA();

    abstract AbstractProductB createProductB();
}

class ConcreteFactory1 extends AbstractFactory {
    AbstractProductA createProductA() {
        return new ProductA1();
    }

    AbstractProductB createProductB() {
        return new ProductB1();
    }
}

class ConcreteFactory2 extends AbstractFactory {
    AbstractProductA createProductA() {
        return new ProductA2();
    }

    AbstractProductB createProductB() {
        return new ProductB2();
    }
}

public class AbstractFactoryTest {
    public static void main(String[] args) {
        AbstractFactory abstractFactory1 = new ConcreteFactory1();
        AbstractProductA productA1 = abstractFactory1.createProductA();
        AbstractProductB productB1 = abstractFactory1.createProductB();
        System.out.println(productA1.getClass());
        System.out.println(productB1.getClass());

        AbstractFactory abstractFactory2 = new ConcreteFactory2();
        AbstractProductA productA2 = abstractFactory2.createProductA();
        AbstractProductB productB2 = abstractFactory2.createProductB();
        System.out.println(productA2.getClass());
        System.out.println(productB2.getClass());


    }

}
