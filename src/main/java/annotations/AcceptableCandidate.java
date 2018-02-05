package annotations;

public @interface AcceptableCandidate {

    String reason() default "";
    boolean previouslyTested() default false;
}
