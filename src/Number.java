public class Number implements Comparable<Number>{
    public long de;
    public long nu;

    public String toString(){
    	if(de==1)return Long.toString(nu);
        return nu+"/"+de;
    }

    public double toDouble() {
    	return (double)nu/(double)de;
    }
    
    public float toFLoat() {
    	return (float)nu/(float)de;
    }
    
    public Number() {
    	this.de=1;
    	this.nu=0;
    }
    
    public Number(int x){
        this.de=1;
        this.nu=x;
    }

    public Number(long x){
        this.de=1;
        this.nu=x;
    }

    public Number(int nu,int de){
        this.de=de;
        this.nu=nu;
        simplify();
    }

    public Number(long nu,long de){
        this.de=de;
        this.nu=nu;
        simplify();
    }

    public void simplify(){
    	if(nu==0) {
    		de=1;
    		return;
    	}
    	if(de<0) {
    		de=-de;
    		nu=-nu;
    	}
        long tmp=gcd(Math.abs(de),Math.abs(nu));
        de/=tmp;
        nu/=tmp;
    }
    
    public static Number abs(Number x) {
    	return new Number(Math.abs(x.nu),Math.abs(x.de));
    }
    
    public Number abs() {
    	return new Number(Math.abs(nu),Math.abs(de));
    }
    
    public Number clone() {
    	return new Number(nu,de);
    }
    
    public static Number add(Number a,Number b){
    	Number tmp=new Number(a.nu*b.de+a.de*b.nu,a.de*b.de);
    	tmp.simplify();
    	return tmp;
    }
    
    public void add(Number x) {
    	Number tmp=new Number(nu*x.de+de*x.nu,de*x.de);
    	tmp.simplify();
    	de=tmp.de;
    	nu=tmp.nu;
    }
    
    public static Number sub(Number a,Number b){
    	Number tmp=new Number(a.nu*b.de-a.de*b.nu,a.de*b.de);
    	tmp.simplify();
    	return tmp;
    }
    
    public void sub(Number x) {
    	Number tmp=new Number(nu*x.de-de*x.nu,de*x.de);
    	tmp.simplify();
    	de=tmp.de;
    	nu=tmp.nu;
    }
    
    public static Number mul(Number a,Number b){
    	Number tmp=new Number(a.nu*b.nu,a.de*b.de);
    	tmp.simplify();
    	return tmp;
    }
    
    public void mul(Number x) {
    	Number tmp=new Number(nu*x.nu,de*x.de);
    	tmp.simplify();
    	de=tmp.de;
    	nu=tmp.nu;
    }
    
    public static Number div(Number a,Number b){
    	Number tmp=new Number(a.nu*b.de,a.de*b.nu);
    	tmp.simplify();
    	return tmp;
    }
    
    public void div(Number x) {
    	Number tmp=new Number(nu*x.de,de*x.nu);
    	tmp.simplify();
    	de=tmp.de;
    	nu=tmp.nu;
    }
    
    public static long gcd(long a,long b) {
    	if(a==0)return b;
    	if(b==0)return a;
    	return gcd(b,a%b);
    }

	@Override
	public int compareTo(Number o) {
		return Long.compare(nu*o.de, de*o.nu);
	}
}