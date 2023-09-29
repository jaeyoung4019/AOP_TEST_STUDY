package test.security.security_test.dto.authorization.enums;

import lombok.Getter;
import org.apache.ibatis.type.MappedTypes;
import test.security.security_test.handler.CodeEnum;
import test.security.security_test.handler.RoleEnumHandler;


import java.util.Arrays;
import java.util.function.Function;

@Getter
public enum AuthoritiesEnum implements CodeEnum {

    NO_GRANT( "USER_001" , "ROLE_USER" , price ->  price , new Options[]{}),
    BRONZE( "USER_002","ROLE_USER" , price ->  (price - Math.round(price.doubleValue() * 0.02)) , new Options[]{Options.NO_ADVERTISEMENT}),
    SILVER( "USER_003","ROLE_USER" , price ->  (price - Math.round(price.doubleValue() * 0.05)) ,new Options[]{Options.NO_ADVERTISEMENT}),
    GOLD( "USER_004", "ROLE_USER" , price ->  (price - Math.round(price.doubleValue() * 0.1)) ,new Options[]{Options.NO_ADVERTISEMENT});

    private final String code;
    private final String role;
    private final Function<Long , Long> discount;
    private final Options[] options;

    AuthoritiesEnum(String code, String role, Function<Long , Long> discount, Options[] options) {
        this.code = code;
        this.role = role;
        this.discount = discount;
        this.options = options;
    }

    public static AuthoritiesEnum findByCodeEnum(String code) {
        return  Arrays.stream(AuthoritiesEnum.values())
                .filter(x -> x.code.equals(code))
                .findAny()
                .orElse(AuthoritiesEnum.NO_GRANT);
    }


    public static Long findByCodeGetDiscount(String code , Long price) {
        return  Arrays.stream(AuthoritiesEnum.values())
                .filter(x -> x.code.equals(code))
                .findAny()
                .map(find -> find.getDiscountFunction(price))
                .orElse(AuthoritiesEnum.NO_GRANT.getDiscountFunction(price));
    }

    private Long getDiscountFunction(Long price){
        return this.discount.apply(price);
    }

    public static AuthoritiesEnum findOptions(Options option){
        return Arrays.stream(AuthoritiesEnum.values())
                .filter(paymentGroup -> hasOption(paymentGroup , option))
                .findAny()
                .orElse(AuthoritiesEnum.NO_GRANT);
    }

    private static boolean hasOption(AuthoritiesEnum Authorities , Options option){
        return Arrays.stream(Authorities.options)
                .anyMatch(matchPayment ->  matchPayment == option);
    }

    @MappedTypes(AuthoritiesEnum.class)
    public static class TypeHandler extends RoleEnumHandler<AuthoritiesEnum> {
        public TypeHandler() {
            super(AuthoritiesEnum.class);
        }
    }


}
