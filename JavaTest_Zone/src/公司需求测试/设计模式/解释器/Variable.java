package 公司需求测试.设计模式.解释器;

//终结符
public class Variable extends Expression {

	public int interpret(Context con) {
		// this为调用interpret方法的Variable对象
		return con.LookupValue(this);
	}
}