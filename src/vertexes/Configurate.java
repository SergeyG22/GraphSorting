package vertexes;

import config.Config;

public interface Configurate {
    void updateConfiguration(Config config);
    String[] getDependency();
}
