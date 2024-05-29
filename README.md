# Demonstrator for classgraph#843 on JBoss EAP 7.4.15

This is an example project that demonstrates usage of [ClassGraph](https://github.com/classgraph/classgraph) in an Java Enterprise application (ear) which leads to [issue #843](https://github.com/classgraph/classgraph/issues/843) in JBoss EAP >= 7.4.15.

## Test steps

```
/ $ mvn install
/ear $ mvn wildfly-jar:package wildfly-jar:run
```

## Expected result

The output of the JBoss server running the test application should contain a line with

> \### Test succesful ###
