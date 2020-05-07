package AST;

import IR.LazyExecutionEndIR;
import IR.LazyExecutionStartIR;
import IR.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LambdaExpression extends ExpressionNode {

  public ParameterList parameters;
  public String retType;
  public Block body;

  @Override
  public ExpressionNode reduce() {
    // TODO retType ???
    Temp t = new Temp();
    ir.emit(new LazyExecutionStartIR(t.toString(), this.evalType , parameters.parameters.stream().map(Value::new).collect(Collectors.toList())));
    int before = newLabel();
    int after = newLabel();
    ir.emitLabel(before);
    body.gen(before, after);
    ir.emitLabel(after);
    ir.emit(new LazyExecutionEndIR());
    return t;
  }

  @Override
  public ExpressionNode gen() {
    return reduce();
  }

  @Override
  public ExpressionNode gen(int before, int after) {
    return gen();
  }

  @Override
  public List<Node> getChildren() {
    return new ArrayList<Node>() {
      {
        add(parameters);
        add(body);
      }
    };
  }
}
