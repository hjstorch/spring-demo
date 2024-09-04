package de.sopracss.demo.expressionlanguage;

import de.sopracss.demo.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

class ExpressionLanguageTest {

    @Test
    void testValue() {

        ExpressionParser parser = new SpelExpressionParser();
        EvaluationContext context = new StandardEvaluationContext(new User());

        String object = (String) parser.parseExpression("'Hello World'").getValue();
        String string = parser.parseExpression("'Hello World'").getValue(String.class);

        context.setVariable("MailAddress", "zak@blacklabel.soc");
        parser.parseExpression("#MailAddress").getValue(context, String.class);

        parser.parseExpression("firstname").setValue(context, "Zak");
        parser.parseExpression("firstname").getValue(context, String.class);
    }
}
