package top.javatool.canal.client.handler;

/**
 * @authorgaofee
 * @date 2019/3/2622:06
 */
public interface MessageHandler<T> {



     void handleMessage(T t);
}
