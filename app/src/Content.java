public class Content {
    private int maximum ;
    private int items = 0;
    private boolean isAlive = false;
    private int buffer;

    public Content() {}

    @Override
    public String toString() {
        return "Max:"+this.maximum+", items:"+this.items+", buffer:"+this.buffer;
    }

    public Content(int maximum) {
        this.maximum = maximum;
        this.buffer = maximum;
    }

    public int getMaximum() {
        return maximum;
    }

    public void setMaximum(int maximum) {
        this.maximum = maximum;
    }

    public int getItems() {
        return items;
    }

    public void setItems(int items) {
        this.items = items;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public int getBuffer() {
        return buffer;
    }

    public void setBuffer(int buffer) {
        this.buffer = buffer;
    }

    public synchronized void put(int data){
        if(this.isAlive || !this.isPut(data))
        {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isAlive = true;
        if(this.buffer >= data){
            this.items = this.items + data;
            this.buffer = this.buffer - data;
        }
        this.isAlive = false;
        notifyAll();
    }

    public synchronized void pop(int data){
        if(this.isAlive || !this.isPop(data)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.isAlive = true;
        if(this.items >=  data){
            this.items = this.items - data;
            this.buffer = this.buffer + data;
        }
        this.isAlive = false;
        notifyAll();
    }

   
  

    public boolean isPop(int value){
        return value <= this.items ? true : false ;
    }

    public boolean isPut(int value){
        return  value <= this.buffer ? true : false ;
    }

}
