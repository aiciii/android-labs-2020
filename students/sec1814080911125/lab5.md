# ʵ���壺Android�洢���
## һ��ʵ��Ŀ��
- �˽�Andoid�Ĵ洢�ֶ�
- ����Android���ļ��洢
- ����Android�����ݿ�洢
## ����ʵ������
- ����ѡ��Ҫ��ʹ�����ݿ�洢

1. ��Ӧ�ò��������ݴ洢�����ݿ��У�
2. ��Ӧ�����н����ͼ��
## ����ʵ�鲽��
1. ����������java�ļ�������ΪDBOpenHelper.java��User.java

2. ����������Activity������ΪLoginActivity.java��RegisterActivity.java��Ϊ��¼�����ע�����

3. ��activity_login.xml���ò���ΪConstraintLayout������6��������ֱ��Ǳ��⡢�û������롢�������롢��ס���롢�û�ע�ᡢ��¼��ť

4. ��activity_registered.xml���ò���ΪConstraintLayout������6��������ֱ��Ƿ��ذ�ť���û������롢�ֻ������롢�������롢ȷ�����롢ע�ᰴť

5. ע��ʱ����ȡ�����ݴ洢�����ݿ���

   ```java
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//��ֹ����
           setContentView(R.layout.activity_registered);
           setTitle("�û�ע��");//��������ĳ��û�ע��
   
           initView();//��ʼ������
           mDBOpenHelper = new DBOpenHelper(this);
       }
   
       private void initView() {
           et_rgsName = findViewById(R.id.et_rgsName);
           et_rgsPhoneNum = findViewById(R.id.et_rgsPhoneNum);
           et_rgsPsw1 = findViewById(R.id.et_rgsPsw1);
           et_rgsPsw2 = findViewById(R.id.et_rgsPsw2);
   
           Button btn_register = findViewById(R.id.btn_rgs);
           ImageView iv_back = findViewById(R.id.iv_back);
   
           //����ע��ҳ�淵�ذ�ť��ע�ᰴť
           iv_back.setOnClickListener(this);
           btn_register.setOnClickListener(this);
       }
   
       @Override
       public void onClick(View v) {
           switch (v.getId()) {
               case R.id.iv_back://���ذ�ť�����ص�¼����
                   Intent intent = new Intent(RegisteredActivity.this, LoginActivity.class);
                   startActivity(intent);
                   finish();
                   break;
               case R.id.btn_rgs://ע�ᰴť
                   //��ȡ�û�������û���������
                   String username = et_rgsName.getText().toString().trim();
                   String password1 = et_rgsPsw1.getText().toString().trim();
                   String password2 = et_rgsPsw2.getText().toString().trim();
                   String phonenum = et_rgsPhoneNum.getText().toString().trim();
                   //ע����֤
                   if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password1) && !TextUtils.isEmpty(password2)) {
                       //�ж����������Ƿ�һ��
                       if (password1.equals(password2)) {
                           //���û�����������뵽���ݿ���
                           mDBOpenHelper.add(username, password2, phonenum);
                           Intent intent1 = new Intent(RegisteredActivity.this, LoginActivity.class);
                           startActivity(intent1);
                           finish();
                           Toast.makeText(this, "��֤ͨ����ע��ɹ�", Toast.LENGTH_SHORT).show();
                       } else {
                           Toast.makeText(this, "�������벻һ��,ע��ʧ��", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       Toast.makeText(this, "ע����Ϣ������,ע��ʧ��", Toast.LENGTH_SHORT).show();
                   }
                   break;
           }
       }
   ```

6. �������ݿ�DBOpenHelper

   ```java
   public class DBOpenHelper extends SQLiteOpenHelper {
   
       //�������ݿ�db
       private SQLiteDatabase db;
   
       //���캯����ָ�������ġ����ݿ���������Ĭ�Ͽ�ֵ���汾��Ϊ1
       DBOpenHelper(Context context){
           super(context,"db_test",null,1);
           db = getReadableDatabase();
       }
   
       //��дonCreate()��onUpgrade()����
       @Override
       public void onCreate(SQLiteDatabase db){
           db.execSQL("CREATE TABLE IF NOT EXISTS user(" +
                   "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                   "name TEXT," +
                   "password TEXT," +
                   "email TEXT," +
                   "phonenum TEXT)"
           );
       }
       //�汾��Ӧ
       @Override
       public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
           db.execSQL("DROP TABLE IF EXISTS user");
           onCreate(db);
       }
   
       //����������
       void add(String name, String password,String phonenum){
           db.execSQL("INSERT INTO user (name,password,email,phonenum) VALUES(?,?,?,?)",new Object[]{name,password,phonenum});
       }
   
   
       //ʹ���α��ȡ���ݴ�ŵ�list������
       ArrayList<User> getAllData(){
           ArrayList<User> list = new ArrayList<User>();
           @SuppressLint("Recycle") Cursor cursor = db.query("user",null,null,null,null,null,"name DESC");
           while(cursor.moveToNext()){
               String name = cursor.getString(cursor.getColumnIndex("name"));
               String phonenum = cursor.getString(cursor.getColumnIndex("phonenum"));
               String password = cursor.getString(cursor.getColumnIndex("password"));
   
               list.add(new User(name,password,phonenum));
           }
           return list;
       }
   }
   ```

7. User.java

   ```java
   public class User {
       private String name;            //�û���
       private String password;        //����
       private String phonenum;        //�ֻ�����
   
       User(String name, String password,String phonenum) {
           this.name = name;
           this.password = password;
           this.phonenum = phonenum;
       }
   
       public String getPhonenum() {
           return phonenum;
       }
   
       public void setPhonenum(String phonenum) {
           this.phonenum = phonenum;
       }
   
       public String getName() {
           return name;
       }
   
       public void setName(String name) {
           this.name = name;
       }
   
       public String getPassword() {
           return password;
       }
   
       public void setPassword(String password) {
           this.password = password;
       }
   
       @Override
       public String toString() {
           return "User{" +
                   "name='" + name + '\'' +
                   ", password='" + password + '\'' +
                   ", phonenum='" + phonenum + '\'' +
                   '}';
       }
   }
   ```

8. ��¼ʱ�����ݿ������ݽ���ƥ�䣬ƥ��ɹ�����ҳ����ת

   ```java
   public void onClick(View v) {
           switch (v.getId()){
               case R.id.tv_Register: //ע��
                   Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);//��ת��ע�����
                   startActivity(intent);
                   finish();
                   break;
               //��¼��֤
               case R.id.btn_Login:
                   //��ȡ��������
                   String name = et_User.getText().toString().trim();
                   String password = et_Psw.getText().toString().trim();
                   //�ж����������Ƿ�Ϊ�գ�����Ϊ���������ݿ������ݽ���ƥ�䣬ƥ��ɹ�����ҳ����ת
                   if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)) {
                       ArrayList<User> data = mDBOpenHelper.getAllData();
                       boolean match = false;
                       boolean match2 = false;
                       for (int i = 0; i < data.size(); i++) {
                           User user = data.get(i);
                           if ((name.equals(user.getName()) && password.equals(user.getPassword()))||
                                   (name.equals(user.getPhonenum())&&password.equals(user.getPassword()))) {
                               userName = user.getName();
                               match = true;
                               if(cb_rmbPsw.isChecked()){
                                   editor.putBoolean("flag",true);
                                   editor.putString("user",user.getName());
                                   editor.putString("psw",user.getPassword());
                                   editor.apply();
                                   match2 = true;
                               }else {
                                   editor.putString("user",user.getName());
                                   editor.putString("psw","");
                                   editor.apply();
                                   match2 = false;
                               }
                               break;
                           } else {
                               match = false;
                           }
                       }
                       if (match) {
                           if(match2){
                               Toast.makeText(this, "�ɹ���ס����", Toast.LENGTH_SHORT).show();
                               cb_rmbPsw.setChecked(true);
                           }
                           Toast.makeText(this, "��¼�ɹ�", Toast.LENGTH_SHORT).show();
                           Runnable target;
                           //���߳�����
                           Thread thread = new Thread(){
                               @Override
                               public void run(){
                                   try {
                                       sleep(2000);//��ʱ2��
                                       String user_name = userName;
                                       Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);//������ת���ɹ��Ľ���
   
                                       startActivity(intent1);
                                       finish();
                                   }catch (Exception e){
                                       e.printStackTrace();
                                   }
                               }
                           };
                           thread.start();//���߳�
                       } else {
                           Toast.makeText(this, "�û��������벻��ȷ������������", Toast.LENGTH_SHORT).show();
                       }
                   } else {
                       Toast.makeText(this, "����������û���������", Toast.LENGTH_SHORT).show();
                   }
                   break;
           }
       }
   ```

9. activity_login.xml��¼����

   ```java
   <TextView
           android:id="@+id/tv_loginview"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@string/logint"
           android:textSize="45sp"
           android:textColor="@color/colorPrimary"
           app:layout_constraintBottom_toTopOf="@+id/guideline2"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintHorizontal_bias="0.129"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="parent"
           app:layout_constraintVertical_bias="0.494" />
   
       <EditText
           android:id="@+id/et_User"
           android:layout_width="350dp"
           android:layout_height="wrap_content"
           android:autofillHints="user"
           android:background="@drawable/round_border"
           android:drawableStart="@drawable/ic_user"
           android:drawablePadding="16dp"
           android:ems="10"
           android:hint="@string/etUser_hint"
           android:inputType="textEmailAddress"
           android:padding="16dp"
           app:layout_constraintBottom_toTopOf="@+id/guideline3"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="parent"
           tools:ignore="LabelFor"
           app:layout_constraintTop_toTopOf="@+id/guideline2"
           android:drawableLeft="@drawable/ic_user" />
   
       <EditText
           android:id="@+id/et_Psw"
           android:layout_width="350dp"
           android:layout_height="wrap_content"
           android:autofillHints=""
           android:background="@drawable/round_border"
           android:drawableStart="@drawable/ic_lock"
           android:drawablePadding="16dp"
           android:ems="10"
           android:hint="@string/pswHint"
           android:inputType="textPassword"
           android:padding="16dp"
           app:layout_constraintBottom_toTopOf="@+id/guideline4"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintHorizontal_bias="0.508"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="@+id/guideline3"
           app:layout_constraintVertical_bias="0.47"
           android:drawableLeft="@drawable/ic_lock" />
   
       <CheckBox
           android:id="@+id/cb_rmbPsw"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:checked="true"
           android:drawingCacheQuality="auto"
           android:shadowColor="@color/colorPrimaryDark"
           android:text="@string/rempsw"
           android:textColor="@color/colorPrimary"
           android:textColorHighlight="@color/colorPrimary"
           android:textColorLink="@color/colorPrimary"
           android:textSize="18sp"
           app:layout_constraintBottom_toTopOf="@+id/guideline5"
           app:layout_constraintEnd_toStartOf="@+id/guideline"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="@+id/guideline4" />
   
       <Button
           android:id="@+id/btn_Login"
           android:layout_width="350dp"
           android:layout_height="wrap_content"
           android:background="@drawable/round_bg"
           android:text="@string/logintxt"
           android:textColor="#FDFAFA"
           android:textSize="18sp"
           app:layout_constraintBottom_toTopOf="@+id/guideline6"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintHorizontal_bias="0.498"
           app:layout_constraintStart_toStartOf="parent"
           app:layout_constraintTop_toTopOf="@+id/guideline5"
           app:layout_constraintVertical_bias="0.538"  />
   
       <TextView
           android:id="@+id/tv_Register"
           android:layout_width="wrap_content"
           android:layout_height="wrap_content"
           android:text="@string/yhzc"
           android:textColor="@color/colorPrimary"
           android:textSize="18sp"
           app:layout_constraintBottom_toTopOf="@+id/guideline5"
           app:layout_constraintEnd_toEndOf="parent"
           app:layout_constraintStart_toStartOf="@+id/guideline"
           app:layout_constraintTop_toTopOf="@+id/guideline4" />
   ```

10. activity_registered.xmlע�����

    ```java
    <ImageView
            android:id="@+id/iv_back"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_marginTop="16dp"
            app:layout_constraintBottom_toTopOf="@+id/guideline8"
            app:layout_constraintEnd_toStartOf="@+id/guideline7"
            app:layout_constraintHorizontal_bias="0.242"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintVertical_bias="0.477"
            app:srcCompat="@drawable/ic_back"
            tools:ignore="VectorDrawableCompat" />
    
        <EditText
            android:id="@+id/et_rgsName"
            android:layout_width="350dp"
            android:layout_height="wrap_content"
            android:autofillHints=""
            android:background="@drawable/round_border"
            android:drawableStart="@drawable/ic_user"
            android:drawableEnd="@drawable/ic_star"
            android:drawablePadding="16dp"
            android:ems="10"
            android:hint="@string/plsu"
            android:inputType="textPersonName"
            android:padding="12dp"
            app:layout_constraintBottom_toTopOf="@+id/guideline9"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="@+id/guideline8"
            android:drawableLeft="@drawable/ic_user"
            android:drawableRight="@drawable/ic_star" />
    
    
        <EditText
            android:id="@+id/et_rgsPhoneNum"
            android:layout_width="350dp"
            android:layout_height="wrap_content"
            android:autofillHints=""
            android:background="@drawable/round_border"
            android:drawableStart="@drawable/ic_phone"
            android:drawablePadding="16dp"
            android:ems="10"
            android:hint="@string/plsohone"
            android:inputType="textPersonName"
            android:padding="12dp"
            app:layout_constraintBottom_toTopOf="@+id/guideline10"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="@+id/guideline9"
            android:drawableLeft="@drawable/ic_phone"
            android:drawableRight="@drawable/ic_star" />
    
        <EditText
            android:id="@+id/et_rgsPsw1"
            android:layout_width="350dp"
            android:layout_height="wrap_content"
            android:autofillHints=""
            android:background="@drawable/round_border"
            android:drawableStart="@drawable/ic_lock"
            android:drawableEnd="@drawable/ic_star"
            android:drawablePadding="16dp"
            android:ems="10"
            android:hint="@string/plspsw"
            android:inputType="textPersonName"
            android:padding="12dp"
            app:layout_constraintBottom_toTopOf="@+id/guideline11"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="@+id/guideline10"
            android:drawableLeft="@drawable/ic_lock"
            android:drawableRight="@drawable/ic_star" />
    
        <EditText
            android:id="@+id/et_rgsPsw2"
            android:layout_width="350dp"
            android:layout_height="wrap_content"
            android:autofillHints=""
            android:background="@drawable/round_border"
            android:drawableStart="@drawable/ic_lock"
            android:drawableEnd="@drawable/ic_star"
            android:drawablePadding="16dp"
            android:ems="10"
            android:hint="@string/plspsw"
            android:inputType="textPersonName"
            android:padding="12dp"
            app:layout_constraintBottom_toTopOf="@+id/guideline12"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="@+id/guideline11"
            android:drawableLeft="@drawable/ic_lock"
            android:drawableRight="@drawable/ic_star" />
    
        <Button
            android:id="@+id/btn_rgs"
            android:layout_width="350dp"
            android:layout_height="wrap_content"
            android:background="@drawable/round_bg"
            android:text="@string/reg"
            android:textColor="#FFFFFF"
            android:textSize="14sp"
            app:layout_constraintBottom_toTopOf="@+id/guideline13"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintHorizontal_bias="0.491"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintTop_toTopOf="@+id/guideline12"
            app:layout_constraintVertical_bias="0.692" />
    ```

## �ġ�ʵ����

![ʵ��5��ͼ1](https://github.com/1nnocent1/android-labs-2020/blob/master/students/sec1814080911125/lab5_1.png)  

![ʵ��5��ͼ2](https://github.com/1nnocent1/android-labs-2020/blob/master/students/sec1814080911125/lab5_2.png) 


## �塢ʵ���ĵ�
����ʵ���Ǵ洢��̣�ѧϰʹ�������ݿ�洢������ʹ�õ���ConstraintLayout���ڱ���ʵ���ж�AS���˸�����˽⡣
