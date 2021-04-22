package vertexes;

import config.Config;

public class F implements Configurate {
    private String[] dependency;

    @Override
    public void updateConfiguration(Config config) {
    }

    public void setDependency(String[] dependency) {
        this.dependency = dependency;
    }

    @Override
    public String[] getDependency() {
        return dependency;
    }

}
