package com.foo;

import com.google.inject.Module;
import io.bootique.BQModuleProvider;

public class HelloModuleProvider implements BQModuleProvider {

    @Override
    public Module module() {
        return new HelloModule();
    }
}
