package com.luo;

public class Address {
    private String addr;
    private String city;
    private String state;
    private String zip;

    public Address() {
    }

    public String getAddr() {
        return this.addr;
    }

    public void setAddr(String aAddr) {
        this.addr = aAddr;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String aCity) {
        this.city = aCity;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String aState) {
        this.state = aState;
    }

    public String getZip() {
        return this.zip;
    }

    public void setZip(String aZip) {
        this.zip = aZip;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Address address = (Address) o;

        if (addr != null ? !addr.equals(address.addr) : address.addr != null) {
            return false;
        }
        if (city != null ? !city.equals(address.city) : address.city != null) {
            return false;
        }
        if (state != null ? !state.equals(address.state) : address.state != null) {
            return false;
        }
        return !(zip != null ? !zip.equals(address.zip) : address.zip != null);

    }

    @Override
    public int hashCode() {
        int result = addr != null ? addr.hashCode() : 0;
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "Address{addr=\'" + this.addr + '\'' + ", city=\'" + this.city + '\'' + ", state=\'" + this.state + '\''
            + ", zip=\'" + this.zip + '\'' + '}';
    }
}

