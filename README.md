## ASM字节码插桩详解


### **1、ASM概述**

   * ASM是一个功能比较齐全的java字节码操作与分析框架，通过ASM框架，我们可以动态的生成类或者增强已有类的功能。

   * ASM可以直接生成二进制.class文件，也可以在类被加载入java虚拟机之前动态改变现有类的行为。

   * java的二进制文件被存储在严格格式定义的.class文件里，这些字节码文件拥有足够的元数据信息用来表示类中的所有元素，
      包括类名称、方法、属性以及java字节码指令。ASM从字节码文件读入这些信息后，能够改变类行为、
     分析类的信息，甚至还可以根据具体的要求生成新的类。

   * ASM 通过树这种数据结构来表示复杂的字节码结构，因为需要处理字节码结构是固定的，
     所以可以利用Visitor(访问者) 设计模式来对树进行遍历，在遍历过程中对字节码进行修改。

### **2、Java 类文件概述**

所谓 Java 类文件，就是通常用 javac 编译器产生的 .class 文件。这些文件具有严格定义的格式。Java 源文件经过 javac 编译器编译之后，将会生成对应的二进制文件。Java 类文件是 8 位字节的二进制流。数据项按顺序存储在 class 文件中，相邻的项之间没有间隔，这使得 class 文件变得紧凑，减少存储空间。一个简单的Hello World程序

    ```
        public class HelloWorld {
            public static void main(String[] args) {
                System.out.println("Hello world");
            }
        }
    ```
经过 javac 编译后，得到的类文件HelloWorld.class，该文件中是由十六进制符号组成的，这一段十六进制符号组成的长串是严格遵守 Java 虚拟机规范。用vim查看HelloWorld.class



####  或者通过gradle 编译本项目成class。原理是用javac

    ```

    task buildJavaFile(type: Exec) {
        def helloWorldJavaPath ="/Users/lixiaodaoaaa/project/java/AsmDemo/src/main/java/com/daollion/study/HelloWorld.java"
        def buildCommand = ['javac',helloWorldJavaPath]
        commandLine buildCommand
    }
    ```
 *  请自行配置 helloWorldJavaPath 这个变量路径 。就是HelloWorld.java文件路径
 *   ./gradlew buildJavaFile
 *   若您配置了 gradle 环境变量 请直接在Terminal中运行 gradle run


```shell
vim HelloWorld.class
```

打开文件后输入
```shell
:%!xxd
```
按回车即可看到如下一串串十六进制符号
![](https://raw.githubusercontent.com/lixiaodaoaaa/publicSharePic/master/20191219144313.png)
---
HelloWorld.class文件构成如下：
---
![](https://raw.githubusercontent.com/lixiaodaoaaa/publicSharePic/master/20191219144356.png)
从上图中可以看到，一个 Java 类文件大致可以归为 10 个项：

* Magic：该项存放了一个 Java 类文件的魔数（magic number），一个 Java 类文件的前 4 个字节被称为它的魔数。每个正确的 Java 类文件都是以 0xCAFEBABE 开头的，这样保证了 Java 虚拟机能很轻松的分辨出 Java 文件和非 Java 文件。

** 有趣的是，魔数的固定值是Java之父James Gosling制定的，为CafeBabe（咖啡宝贝），而Java的图标为一杯咖啡。**

* Version：该项存放了 Java 类文件的版本信息

* Constant Pool：常量池中存储两类常量：字面量与符号引用。字面量为文本字符串和代码中声明为Final的常量值，符号引用如类和接口的全局限定名、字段的名称和描述符、方法的名称和描述符。

* Access_flag：该项指明了该文件中定义的是类还是接口（一个 class 文件中只能有一个类或接口），同时还指名了类或接口的访问标志，如 public，private, abstract 等信息。

* This Class：指向表示该类全限定名称的字符串常量的指针。

* Super Class：指向表示父类全限定名称的字符串常量的指针。

* Interfaces：一个指针数组，存放了该类或父类实现的所有接口名称的字符串常量的指针。

* Fields：该项对类或接口中声明的字段进行了细致的描述。需要注意的是，fields 列表中仅列出了本类或接口中的字段，并不包括从超类和父接口继承而来的字段。

* Methods：该项对类或接口中声明的方法进行了细致的描述。例如方法的名称、参数和返回值类型等。需要注意的是，methods 列表里仅存放了本类或本接口中的方法，并不包括从超类和父接口继承而来的方法。

* Class attributes：该项存放了在该文件中类或接口所定义的属性的基本信息。

### **3、ASM库的结构**

Core：为其他包提供基础的读、写、转化Java字节码和定义的API，并且可以生成Java字节码和实现大部分字节码的转换。

Tree：提供了 Java 字节码在内存中的表现

Commons：提供了一些常用的简化字节码生成、转换的类和适配器

Util：包含一些帮助类和简单的字节码修改类，有利于在开发或者测试中使用

XML：提供一个适配器将XML和SAX-comliant转化成字节码结构，可以允许使用XSLT去定义字节码转化


### 文章更多 参考

[ASM字节码插桩详解](https://www.jianshu.com/p/abba54baf617?utm_source=desktop&utm_medium=timeline)




