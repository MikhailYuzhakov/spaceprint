PGDMP      *    	            }         
   spaceprint #   16.6 (Ubuntu 16.6-0ubuntu0.24.04.1) #   16.6 (Ubuntu 16.6-0ubuntu0.24.04.1) B    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16388 
   spaceprint    DATABASE     v   CREATE DATABASE spaceprint WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'ru_RU.UTF-8';
    DROP DATABASE spaceprint;
                postgres    false                        2615    16389 
   spaceprint    SCHEMA        CREATE SCHEMA spaceprint;
    DROP SCHEMA spaceprint;
                postgres    false            �            1259    16413    accounts    TABLE     o   CREATE TABLE spaceprint.accounts (
    number integer,
    balance double precision,
    id bigint NOT NULL
);
     DROP TABLE spaceprint.accounts;
    
   spaceprint         heap    postgres    false    6            �            1259    16416    accounts_id_seq    SEQUENCE     |   CREATE SEQUENCE spaceprint.accounts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 *   DROP SEQUENCE spaceprint.accounts_id_seq;
    
   spaceprint          postgres    false    221    6            �           0    0    accounts_id_seq    SEQUENCE OWNED BY     K   ALTER SEQUENCE spaceprint.accounts_id_seq OWNED BY spaceprint.accounts.id;
       
   spaceprint          postgres    false    222            �            1259    16390    orders    TABLE     �  CREATE TABLE spaceprint.orders (
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
   spaceprint         heap    postgres    false    6            �           0    0    TABLE orders    COMMENT     N   COMMENT ON TABLE spaceprint.orders IS 'База данныз заказов';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.result    COMMENT     �   COMMENT ON COLUMN spaceprint.orders.result IS 'Хранится запись об итоговом результате заказа.';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.image_uri    COMMENT     \   COMMENT ON COLUMN spaceprint.orders.image_uri IS 'Изображение к заказу.';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.note    COMMENT     D   COMMENT ON COLUMN spaceprint.orders.note IS 'Примечания';
       
   spaceprint          postgres    false    216            �           0    0    COLUMN orders.npv    COMMENT     K   COMMENT ON COLUMN spaceprint.orders.npv IS 'Доход от заказа';
       
   spaceprint          postgres    false    216            �            1259    16395    orders_id_seq    SEQUENCE     z   CREATE SEQUENCE spaceprint.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 (   DROP SEQUENCE spaceprint.orders_id_seq;
    
   spaceprint          postgres    false    216    6            �           0    0    orders_id_seq    SEQUENCE OWNED BY     G   ALTER SEQUENCE spaceprint.orders_id_seq OWNED BY spaceprint.orders.id;
       
   spaceprint          postgres    false    217            �            1259    16396    payment_settings    TABLE       CREATE TABLE spaceprint.payment_settings (
    job_price integer NOT NULL,
    depreciation double precision NOT NULL,
    electricity_price double precision NOT NULL,
    printer_power double precision NOT NULL,
    id bigint NOT NULL,
    created_date date NOT NULL
);
 (   DROP TABLE spaceprint.payment_settings;
    
   spaceprint         heap    postgres    false    6            �           0    0    TABLE payment_settings    COMMENT     x   COMMENT ON TABLE spaceprint.payment_settings IS 'Параметры рассчета стоимости заказа.';
       
   spaceprint          postgres    false    218            �           0    0 $   COLUMN payment_settings.depreciation    COMMENT     m   COMMENT ON COLUMN spaceprint.payment_settings.depreciation IS 'Аммортизация принтера.
';
       
   spaceprint          postgres    false    218            �           0    0 $   COLUMN payment_settings.created_date    COMMENT     ~   COMMENT ON COLUMN spaceprint.payment_settings.created_date IS 'Дата обновления параметров цены.';
       
   spaceprint          postgres    false    218            �            1259    16399    payment_settings_id_seq    SEQUENCE     �   CREATE SEQUENCE spaceprint.payment_settings_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 2   DROP SEQUENCE spaceprint.payment_settings_id_seq;
    
   spaceprint          postgres    false    218    6            �           0    0    payment_settings_id_seq    SEQUENCE OWNED BY     [   ALTER SEQUENCE spaceprint.payment_settings_id_seq OWNED BY spaceprint.payment_settings.id;
       
   spaceprint          postgres    false    219            �            1259    16520    roles    TABLE     �   CREATE TABLE spaceprint.roles (
    id bigint NOT NULL,
    name character varying(50) NOT NULL,
    description character varying(255)
);
    DROP TABLE spaceprint.roles;
    
   spaceprint         heap    postgres    false    6            �            1259    16519    roles_id_seq    SEQUENCE     �   CREATE SEQUENCE spaceprint.roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE spaceprint.roles_id_seq;
    
   spaceprint          postgres    false    229    6            �           0    0    roles_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE spaceprint.roles_id_seq OWNED BY spaceprint.roles.id;
       
   spaceprint          postgres    false    228            �            1259    16400    services    TABLE       CREATE TABLE spaceprint.services (
    name character varying(20) NOT NULL,
    description character varying(200),
    price integer,
    uri character varying(200),
    image_uri character varying(200),
    id bigint DEFAULT nextval('public.inc'::regclass) NOT NULL
);
     DROP TABLE spaceprint.services;
    
   spaceprint         heap    postgres    false    6            �           0    0    COLUMN services.uri    COMMENT     k   COMMENT ON COLUMN spaceprint.services.uri IS 'Имя страницы с описанием услуги';
       
   spaceprint          postgres    false    220            �            1259    16601 
   user_roles    TABLE     y   CREATE TABLE spaceprint.user_roles (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    role_id bigint NOT NULL
);
 "   DROP TABLE spaceprint.user_roles;
    
   spaceprint         heap    postgres    false    6            �            1259    16600    user_roles_id_seq    SEQUENCE     �   CREATE SEQUENCE spaceprint.user_roles_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 ,   DROP SEQUENCE spaceprint.user_roles_id_seq;
    
   spaceprint          postgres    false    6    233            �           0    0    user_roles_id_seq    SEQUENCE OWNED BY     O   ALTER SEQUENCE spaceprint.user_roles_id_seq OWNED BY spaceprint.user_roles.id;
       
   spaceprint          postgres    false    232            �            1259    16474    users    TABLE     #  CREATE TABLE spaceprint.users (
    id bigint NOT NULL,
    username character varying(50) NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    middle_name character varying(50),
    phone_number character varying(50),
    password character varying(255) NOT NULL,
    email character varying(100) NOT NULL,
    enabled boolean DEFAULT true NOT NULL,
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);
    DROP TABLE spaceprint.users;
    
   spaceprint         heap    postgres    false    6            �            1259    16473    users_id_seq    SEQUENCE     �   CREATE SEQUENCE spaceprint.users_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 '   DROP SEQUENCE spaceprint.users_id_seq;
    
   spaceprint          postgres    false    6    227            �           0    0    users_id_seq    SEQUENCE OWNED BY     E   ALTER SEQUENCE spaceprint.users_id_seq OWNED BY spaceprint.users.id;
       
   spaceprint          postgres    false    226            �           2604    16417    accounts id    DEFAULT     r   ALTER TABLE ONLY spaceprint.accounts ALTER COLUMN id SET DEFAULT nextval('spaceprint.accounts_id_seq'::regclass);
 >   ALTER TABLE spaceprint.accounts ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    222    221            �           2604    16403 	   orders id    DEFAULT     n   ALTER TABLE ONLY spaceprint.orders ALTER COLUMN id SET DEFAULT nextval('spaceprint.orders_id_seq'::regclass);
 <   ALTER TABLE spaceprint.orders ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    217    216            �           2604    16404    payment_settings id    DEFAULT     �   ALTER TABLE ONLY spaceprint.payment_settings ALTER COLUMN id SET DEFAULT nextval('spaceprint.payment_settings_id_seq'::regclass);
 F   ALTER TABLE spaceprint.payment_settings ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    219    218            �           2604    16528    roles id    DEFAULT     l   ALTER TABLE ONLY spaceprint.roles ALTER COLUMN id SET DEFAULT nextval('spaceprint.roles_id_seq'::regclass);
 ;   ALTER TABLE spaceprint.roles ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    229    228    229            �           2604    16607    user_roles id    DEFAULT     v   ALTER TABLE ONLY spaceprint.user_roles ALTER COLUMN id SET DEFAULT nextval('spaceprint.user_roles_id_seq'::regclass);
 @   ALTER TABLE spaceprint.user_roles ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    232    233    233            �           2604    16497    users id    DEFAULT     l   ALTER TABLE ONLY spaceprint.users ALTER COLUMN id SET DEFAULT nextval('spaceprint.users_id_seq'::regclass);
 ;   ALTER TABLE spaceprint.users ALTER COLUMN id DROP DEFAULT;
    
   spaceprint          postgres    false    226    227    227            �          0    16413    accounts 
   TABLE DATA           ;   COPY spaceprint.accounts (number, balance, id) FROM stdin;
 
   spaceprint          postgres    false    221   9L       �          0    16390    orders 
   TABLE DATA           �   COPY spaceprint.orders (description, date_start, person_id, deadline, model_price, print_price, price, stage, result, image_uri, id, modeling_time, printing_time, extra_charge, plastic_cost, quantity, note, npv) FROM stdin;
 
   spaceprint          postgres    false    216   mL       �          0    16396    payment_settings 
   TABLE DATA           {   COPY spaceprint.payment_settings (job_price, depreciation, electricity_price, printer_power, id, created_date) FROM stdin;
 
   spaceprint          postgres    false    218   �N       �          0    16520    roles 
   TABLE DATA           :   COPY spaceprint.roles (id, name, description) FROM stdin;
 
   spaceprint          postgres    false    229   YO       �          0    16400    services 
   TABLE DATA           T   COPY spaceprint.services (name, description, price, uri, image_uri, id) FROM stdin;
 
   spaceprint          postgres    false    220   �O       �          0    16601 
   user_roles 
   TABLE DATA           >   COPY spaceprint.user_roles (id, user_id, role_id) FROM stdin;
 
   spaceprint          postgres    false    233   jR       �          0    16474    users 
   TABLE DATA           �   COPY spaceprint.users (id, username, first_name, last_name, middle_name, phone_number, password, email, enabled, created_at, updated_at) FROM stdin;
 
   spaceprint          postgres    false    227   �R       �           0    0    accounts_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('spaceprint.accounts_id_seq', 2, true);
       
   spaceprint          postgres    false    222            �           0    0    orders_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('spaceprint.orders_id_seq', 141, true);
       
   spaceprint          postgres    false    217            �           0    0    payment_settings_id_seq    SEQUENCE SET     I   SELECT pg_catalog.setval('spaceprint.payment_settings_id_seq', 8, true);
       
   spaceprint          postgres    false    219            �           0    0    roles_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('spaceprint.roles_id_seq', 1, false);
       
   spaceprint          postgres    false    228            �           0    0    user_roles_id_seq    SEQUENCE SET     D   SELECT pg_catalog.setval('spaceprint.user_roles_id_seq', 1, false);
       
   spaceprint          postgres    false    232            �           0    0    users_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('spaceprint.users_id_seq', 1, false);
       
   spaceprint          postgres    false    226            �           2606    16422    accounts accounts_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY spaceprint.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY spaceprint.accounts DROP CONSTRAINT accounts_pkey;
    
   spaceprint            postgres    false    221            �           2606    16406    orders orders_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY spaceprint.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY spaceprint.orders DROP CONSTRAINT orders_pkey;
    
   spaceprint            postgres    false    216            �           2606    16408 &   payment_settings payment_settings_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY spaceprint.payment_settings
    ADD CONSTRAINT payment_settings_pkey PRIMARY KEY (id);
 T   ALTER TABLE ONLY spaceprint.payment_settings DROP CONSTRAINT payment_settings_pkey;
    
   spaceprint            postgres    false    218            �           2606    16527    roles roles_name_key 
   CONSTRAINT     S   ALTER TABLE ONLY spaceprint.roles
    ADD CONSTRAINT roles_name_key UNIQUE (name);
 B   ALTER TABLE ONLY spaceprint.roles DROP CONSTRAINT roles_name_key;
    
   spaceprint            postgres    false    229            �           2606    16530    roles roles_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY spaceprint.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY spaceprint.roles DROP CONSTRAINT roles_pkey;
    
   spaceprint            postgres    false    229            �           2606    16442    services services_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY spaceprint.services
    ADD CONSTRAINT services_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY spaceprint.services DROP CONSTRAINT services_pkey;
    
   spaceprint            postgres    false    220            �           2606    16609    user_roles user_roles_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY spaceprint.user_roles
    ADD CONSTRAINT user_roles_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY spaceprint.user_roles DROP CONSTRAINT user_roles_pkey;
    
   spaceprint            postgres    false    233            �           2606    16496    users users_email_key 
   CONSTRAINT     U   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_email_key UNIQUE (email);
 C   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_email_key;
    
   spaceprint            postgres    false    227            �           2606    16488    users users_first_name_key 
   CONSTRAINT     _   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_first_name_key UNIQUE (first_name);
 H   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_first_name_key;
    
   spaceprint            postgres    false    227            �           2606    16490    users users_last_name_key 
   CONSTRAINT     ]   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_last_name_key UNIQUE (last_name);
 G   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_last_name_key;
    
   spaceprint            postgres    false    227            �           2606    16492    users users_middle_name_key 
   CONSTRAINT     a   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_middle_name_key UNIQUE (middle_name);
 I   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_middle_name_key;
    
   spaceprint            postgres    false    227            �           2606    16494    users users_phone_number_key 
   CONSTRAINT     c   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_phone_number_key UNIQUE (phone_number);
 J   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_phone_number_key;
    
   spaceprint            postgres    false    227            �           2606    16499    users users_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_pkey;
    
   spaceprint            postgres    false    227            �           2606    16486    users users_username_key 
   CONSTRAINT     [   ALTER TABLE ONLY spaceprint.users
    ADD CONSTRAINT users_username_key UNIQUE (username);
 F   ALTER TABLE ONLY spaceprint.users DROP CONSTRAINT users_username_key;
    
   spaceprint            postgres    false    227            �   $   x�3�41243��4�2�44�4�45�4����� A�O      �   Y  x�mRMn1^{N�����g�FU$�e�f{*6��M� u�;��R��@��
�7������=�o�?�s�����SX�|�U��
��:l/��qϡ�o����\�O��
nq�i�%�s?^��񊔼T�VVD(I�Ɔ%{��h����GC��I�:�Y�Eɔ�[V+7a��\h��r����	��#8�<M�>���Hf/�5M%��{DX���,-#Zl��K��i|g�xlV�D��\S�f�,י�E╦������i"��L��ѦҤ���T�(#+�t�?|r8: G��H�p<"B���uŜ�S���5�J���*�LWȚ�DT���4I$���
��,̄o�yh)�ʘg�8�V&��G^��1D�
�$��}��?=;�����5��. �I@��q�q!h���դ�K �d�D�i �m���U��F
<�c�߾,�([�W�ҵ�uV��0m���|��+���a�A7:��VLmY�T8�ǝ�-}�=j���-xA�#Jf�	���7�,L�/OO�h�QϦ�]�V)m�s�[D9��u��u`�W�)�QGmj�����F�cQ��*��}LN�5�x1(��7�r�      �   s   x���11k��O�`���q\l�I��mX���كJ��]/{徖G��9Ղ��� �C���6%����j<�;����Cb���mz ��wB�����
O��O0�M;�����0/�      �   H   x�3�v⼰�b����.콰�b��6\�a��Ƌ=
@ƾ�/� �]��o��������� 
�'1      �   �  x�}T�n�0];_�}�ȉ��YS�@����8������
R]�$���a�tf��q����V���=��{�����]��~���R?��xe��;w/�0�gqy�gF_~��u�Ga�_b��G�8'7h��u;/��t�v5όh�RFr��S��XչZ6yok�%3�\M�b��x�z����#�߷��},��{D�0#����"����4|�	�c�b�<EPX���@ A=t�i{�ٺ�5P�l���g�����U۴�llY
��B��-K�W`��^8 d��T2v����ٟ�#�8���P�G)��p%� �C��n�$M�=�����
�͍���o�a�j�kٖ\�&����l��U]Ԡ�-�,J< �c�A�\/���2�,��Dԏb���6R;R�= �l1�v�OY�[�ۮ�L&��Jre���u�	n*U�MVٙ�W'w[�,����On���[�(�*�O@4���&�9S���s�UM�USn���֩Bwu'��"�"�|qi��1�?n>��,e}�����|��c�����GgLo�"X��M{�ɶ�k��u����J�X�\�V�
f�U�������gN�	)����G.��y��\?�X�q8��ݎ�o;�Z[5�ɸ�-���aTQqYX���6Y%")�<�%I�!B�l      �      x�3�4�4����� �X      �   �   x�3��,K��0�¦.��0���[.6\�
��8/L���b�v_�	\�q��S����������ܘ3�<������l�Cznbf�^r~.g	��������������������������%)�=... �d7�     