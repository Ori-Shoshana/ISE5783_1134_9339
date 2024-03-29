package primitives;

/**
 * A class representing the material properties of an object.
 */
public class Material {

    /**
     * The material diffuse coefficient
     */
    public Double3 kD = Double3.ZERO;

    /**
     * The material specular coefficient
     */
    public Double3 kS = Double3.ZERO;

    /**
     *  Kt - transparency component
     * 0.0 is opaque
     * 1.0 is clear
     */
    public Double3 kT = Double3.ZERO;

    /**
     *  Kr - reflection component
     * 0.0 is matte
     * 1.0 is very reflexive
     */
    public Double3 kR = Double3.ZERO;
    /**
     * The material shininess
     */
    public int nShininess = 0;

    /**
     * Sets the diffuse coefficient of the material.
     *
     * @param kD The diffuse coefficient to set.
     * @return This Material object with the updated diffuse coefficient.
     */
    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the diffuse coefficient of the material.
     *
     * @param kD The diffuse coefficient to set.
     * @return This Material object with the updated diffuse coefficient.
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }


    /**
     * Sets the specular coefficient of the material.
     *
     * @param kS The specular coefficient to set.
     * @return This Material object with the updated specular coefficient.
     */
    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the specular coefficient of the material.
     *
     * @param kS The specular coefficient to set.
     * @return This Material object with the updated specular coefficient.
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the shininess of the material.
     *
     * @param nShininess The shininess to set.
     * @return This Material object with the updated shininess.
     */
    public Material setnShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
    /**
     * Sets the transparency coefficient of the material.
     *
     * @param kT The transparency coefficient to set.
     * @return This Material object with the updated transparency coefficient.
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The reflection coefficient to set.
     * @return This Material object with the updated reflection coefficient.
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }
    /**
     * Sets the transparency coefficient of the material.
     *
     * @param kT The transparency coefficient to set.
     * @return This Material object with the updated transparency coefficient.
     */
    public Material setkT(double kT) {
        this.kT=new Double3(kT);
        return this;
    }
    /**
     * Sets the reflection coefficient of the material.
     *
     * @param kR The reflection coefficient to set.
     * @return This Material object with the updated reflection coefficient.
     */
    public Material setkR(double kR) {
        this.kR=new Double3(kR);
        return this;
    }
}