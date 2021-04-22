package vertexes;

import config.Config;

public class E implements Configurate {
    private String[] dependency;

    @Override
    public void updateConfiguration(Config config) {
        config.getConfig();
    }

    public void setDependency(String[] dependency) {
        this.dependency = dependency;
    }

    @Override
    public String[] getDependency() {
        return dependency;
    }
}
