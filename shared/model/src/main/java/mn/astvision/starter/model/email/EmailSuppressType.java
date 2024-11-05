package mn.astvision.starter.model.email;

public enum EmailSuppressType {

    BOUNCE, // мэйл илгээх үед bounce хийсэн
    COMPLAINT, // хэрэглэгч талаас complain хийсэн
    UNSUBSCRIBE // хэрэглэгч мэйл хүлээн авахгүй unsubscribe хийсэн
}
