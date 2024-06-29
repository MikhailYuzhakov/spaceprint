PGDMP                      |         
   spaceprint    16.2    16.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16397 
   spaceprint    DATABASE     ~   CREATE DATABASE spaceprint WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Russian_Russia.1251';
    DROP DATABASE spaceprint;
                postgres    false                        2615    16398 
   spaceprint    SCHEMA        CREATE SCHEMA spaceprint;
    DROP SCHEMA spaceprint;
                postgres    false            �            1259    16399    orders    TABLE     �  CREATE TABLE spaceprint.orders (
    description character varying(400),
    date_start date,
    person_id bigint,
    deadline date,
    model_price integer,
    print_price integer,
    price integer,
    stage character varying(50),
    result character varying,
    image_uri character varying(200),
    id bigint NOT NULL,
    modeling_time integer,
    printing_time integer,
    extra_charge integer,
    plastic_cost integer,
    quantity integer,
    note character varying(600),
    npv integer
);
    DROP TABLE spaceprint.orders;
    
   spaceprint         heap    postgres    false    6            �           0    0    TABLE orders    COMMENT     N   COMMENT ON TABLE spaceprint.orders IS 'База данныз заказов';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.result    COMMENT     �   COMMENT ON COLUMN spaceprint.orders.result IS 'Хранится запись об итоговом результате заказа.';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.image_uri    COMMENT     \   COMMENT ON COLUMN spaceprint.orders.image_uri IS 'Изображение к заказу.';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.note    COMMENT     D   COMMENT ON COLUMN spaceprint.orders.note IS 'Примечания';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.npv    COMMENT     K   COMMENT ON COLUMN spaceprint.orders.npv IS 'Доход от заказа';
       
   spaceprint          postgres    false    216            �            1259    24580    orders_id_seq    SEQUENCE     z   CREATE SEQUENCE spaceprint.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE spaceprint.orders_id_seq;
    
   spaceprint          postgres    false    216    6            �           0    0    orders_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE spaceprint.orders_id_seq OWNED BY spaceprint.orders.id;
       
   spaceprint          postgres    false    218            �            1259    24590    payment_settings    TABLE       CREATE TABLE spaceprint.payment_settings (
    job_price integer NOT NULL,
    depreciation double precision NOT NULL,
    electricity_price double precision NOT NULL,
    printer_power double precision NOT NULL,
    id bigint NOT NULL,
    created_date date NOT NULL
);
 (   DROP TABLE spaceprint.payment_settings;
    
   spaceprint         heap    postgres    false    6            �           0    0    TABLE payment_settings    COMMENT     x   COMMENT ON TABLE spaceprint.payment_settings IS 'Параметры рассчета стоимости заказа.';
       
   spaceprint          postgres    false    219            �           0    0 $   COLUMN payment_settings.depreciation    COMMENT     m   COMMENT ON COLUMN spaceprint.payment_settings.depreciation IS 'Аммортизация принтера.
';
       
   spaceprint          postgres    false    219            �           0    0 $   COLUMN payment_settings.created_date    COMMENT     ~   COMMENT ON COLUMN spaceprint.payment_settings.created_date IS 'Дата обновления параметров цены.';
       
   spaceprint          postgres    false    219            �            1259    24596    payment_settings_id_seq    SEQUENCE     �   CREATE SEQUENCE spaceprint.payment_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE spaceprint.payment_settings_id_seq;
    
   spaceprint          postgres    false    6    219            �           0    0    payment_settings_id_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE spaceprint.payment_settings_id_seq OWNED BY spaceprint.payment_settings.id;
       
   spaceprint          postgres    false    220            �            1259    16404    services    TABLE     �   CREATE TABLE spaceprint.services (
    id bigint NOT NULL,
    name character varying(20) NOT NULL,
    description character varying(200),
    price integer,
    uri character varying(200)
);
     DROP TABLE spaceprint.services;
    
   spaceprint         heap    postgres    false    6            �           0    0    COLUMN services.uri    COMMENT     k   COMMENT ON COLUMN spaceprint.services.uri IS 'Имя страницы с описанием услуги';
       
   spaceprint          postgres    false    217            $           2604    24581 	   orders id    DEFAULT     n   ALTER TABLE ONLY spaceprint.orders ALTER COLUMN id SET DEFAULT nextval('spaceprint.orders_id_seq'::regclass);
 <   ALTER TABLE spaceprint.orders ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    218    216            %           2604    24597    payment_settings id    DEFAULT     �   ALTER TABLE ONLY spaceprint.payment_settings ALTER COLUMN id SET DEFAULT nextval('spaceprint.payment_settings_id_seq'::regclass);
 F   ALTER TABLE spaceprint.payment_settings ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    220    219            �          0    16399    orders 
   TABLE DATA           �   COPY spaceprint.orders (description, date_start, person_id, deadline, model_price, print_price, price, stage, result, image_uri, id, modeling_time, printing_time, extra_charge, plastic_cost, quantity, note, npv) FROM stdin;
 
   spaceprint          postgres    false    216   �"       �          0    24590    payment_settings 
   TABLE DATA           {   COPY spaceprint.payment_settings (job_price, depreciation, electricity_price, printer_power, id, created_date) FROM stdin;
 
   spaceprint          postgres    false    219   �$       �          0    16404    services 
   TABLE DATA           I   COPY spaceprint.services (id, name, description, price, uri) FROM stdin;
 
   spaceprint          postgres    false    217   9%       �           0    0    orders_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('spaceprint.orders_id_seq', 22, true);
       
   spaceprint          postgres    false    218            �           0    0    payment_settings_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('spaceprint.payment_settings_id_seq', 2, true);
       
   spaceprint          postgres    false    220            '           2606    24583    orders orders_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY spaceprint.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY spaceprint.orders DROP CONSTRAINT orders_pkey;
    
   spaceprint            postgres    false    216            +           2606    24599 &   payment_settings payment_settings_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY spaceprint.payment_settings
    ADD CONSTRAINT payment_settings_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY spaceprint.payment_settings DROP CONSTRAINT payment_settings_pkey;
    
   spaceprint            postgres    false    219            )           2606    16410    services services_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY spaceprint.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY spaceprint.services DROP CONSTRAINT services_pkey;
    
   spaceprint            postgres    false    217            �     x���KnA��=���V��Ċ�`��l��3�GH!@Y�d�8�$0�ď\��F�'��X�!���w����Z���5�5�p?`��aW����(\�g�lѹ�4)n��
n�S�0v��N��R3n�4D��	N8���'Ӳ�&������x|rBb��X���0mâ���g�V�IF�޿{K�G%�|Ը�7����3��B��lb�t�(�bu1�k_�{՟ST���~>��3��~gs����:��5G��Di��4����IyD����5�bJW��*DV%�hRL���s|,�0��S��N�;�~>l��B~�1��p�3�j0�K��|�ا���I�s�:�Ǚ;?�e2-I]��x���`z�V���Ap��\Ti (9�
�Q�<9A�
����\ܐ&���{V��-�L~�vG�b���'���<����{�Ɋ�LJ��c�t��<i|�)j�a���=0Ry�P�쮼��+�V)"��
�?x��X��վx3*���aAj      �   :   x�m��  �f�(�J���Tb��,;�0�}�����Ks�;��wDW�fv��      �   �  x�}RMN�P^������x ����!��)ʺEP��D�P�B)�\a�F~�*EMpѾ�����))z��V��-y��ܧ�G;�֔h؞h��5M�f��O��9���Z��(�Q"}~�м�;�''��ϫ�V�}Ӹ�7ϭ��
��+�!j��?��SEo��D��� 
������)' �
 ���J�s���m���	
�=*z�>4�e0�}}T9Ѧg� 
(RX�!���3�Ŀ��|�$�bUΫ+��4���xW+�u��@�;d���?tM��1�W#�ȚEI)���B?44z]�jvC(���ѥ����Ԉ;�8B
E�#E[3�'KNda�.�>��5�C7>Me�n�a.�+@�F�m;ց�T@�p{iy�q�S��N�����Hsdi���M�!z���fw�ӜeY_��x     