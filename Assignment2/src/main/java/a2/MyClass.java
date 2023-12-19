package a2;

public class MyClass {
   public String name;

   private int num;

   private int a;
   private int b;

   public MyClass()
   {}
   public MyClass(String name,int num)
   {
      this.name=name;
      this.num=num;
   }

   public int GetNum()
   {
      return num;
   }

   public void SetNum(int num)
   {
      this.num=num;
   }

   public int getA(){return a;}
   public int getB(){return b;}

   public void setA(int a){this.a=a;}

   public void setB(int b){this.b=b;}



   public void Init(int a,int b)
   {

      this.a=a;
      this.b=b;
   }


   @AddMethod
   public void Add()
   {
      this.num=a+b;
   }


   @SubMethod
   public void Sub()
   {
      this.num= a-b;
   }

   public void Multi()
   {
      this.num= a*b;
   }






}
