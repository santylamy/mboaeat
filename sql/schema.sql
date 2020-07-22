create table if not exists persons
(
    person_id   bigint      not null
        constraint persons_pkey
            primary key,
    email       varchar(50) not null,
    name        varchar(250),
    first_name  varchar(250),
    middle_name varchar(250)
);

create table if not exists accounts
(
    account_id  bigint not null
        constraint account_pkey
            primary key,
    person_id   bigint not null
        constraint accounts_persons_fk
            references persons,
    customer_id bigint
);

create unique index if not exists persons_email_uindex
    on persons (email);

create table if not exists physical_account
(
    pwd        varchar(250),
    hash       varchar(250),
    account_id bigint not null
        constraint physical_account_pk
            primary key
        constraint physical_account_accounts_fk
            references accounts
);

create table if not exists moral_account
(
    account_id bigint not null
        constraint moral_account_pk
            primary key
        constraint moral_account_accounts_fk
            references accounts
);

create table if not exists images
(
    image_id    bigint not null
        constraint images_pk
            primary key,
    source_id   bigint not null,
    image_title varchar(50),
    image_name  varchar(100),
    image_path  varchar(10)
);

create table if not exists products
(
    product_id        bigint      not null
        constraint products_pk
            primary key,
    created_date      timestamp   not null,
    modified_date     timestamp   not null,
    product_desc_fr   text,
    product_name_fr   varchar(50) not null,
    product_type_code varchar(50) not null,
    product_name_en   varchar(50),
    product_desc_en   text
);

create table if not exists payments_mode
(
    pay_mod_id bigint not null
        constraint payments_mode_pk
            primary key
);

create table if not exists product_prices
(
    product_price_id bigint    not null
        constraint product_prices_pk
            primary key,
    date_from        timestamp not null,
    end_date         timestamp,
    product_id       bigint    not null
        constraint product_prices_products_fk
            references products,
    product_price    numeric
);

create table if not exists address
(
    address_id          bigint not null
        constraint address_pk
            primary key,
    country             varchar(30),
    address_type        varchar(20),
    mobile_phone_number varchar(30),
    street_desc_one     varchar(50),
    street_desc_two     varchar(50),
    street_post_box     varchar(10),
    street_city         varchar(30),
    name                varchar(50),
    first_name          varchar(50),
    email               varchar(30)
);

create table if not exists clients
(
    client_id          bigint not null
        constraint clients_pk
            primary key,
    address_id         bigint
        constraint clients_address_fk
            references address,
    account_id         bigint
        constraint clients_accounts_fk
            references accounts,
    client_name        varchar(50),
    fixed_phone_number varchar(50)
);

create table if not exists client_payment_methods
(
    client_payment_method_id bigint      not null
        constraint client_payment_methods_pk
            primary key,
    client_id                bigint
        constraint client_payment_methods_clients_fk
            references clients,
    method_code              varchar(50) not null,
    payment_status_code      varchar(30) not null
);

create table if not exists orders
(
    order_id                   bigint    not null
        constraint orders_pk
            primary key,
    order_date                 timestamp not null,
    created_date               timestamp not null,
    modified_date              timestamp not null,
    order_status_code          varchar(30),
    total_price                numeric,
    total_price_tva            numeric,
    date_order_paid            timestamp,
    customer_payment_method_id bigint    not null
        constraint orders_client_payment_methods_fk
            references client_payment_methods
        constraint orders_payments_mode_fk
            references payments_mode,
    client_id                  bigint
        constraint orders_clients_fk
            references clients,
    date_order_placed          timestamp not null
);

create table if not exists client_orders_delivery
(
    client_order_delivery_id bigint      not null
        constraint client_orders_delivery_pk
            primary key,
    order_id                 bigint
        constraint client_orders_delivery_orders_fk
            references orders,
    delivery_status_code     varchar(30) not null
);

create table if not exists menu_category
(
    menu_category_id bigint      not null
        constraint menu_category_pk
            primary key,
    code             varchar(10) not null,
    name_fr          varchar(50),
    name_en          varchar(50)
);

create table if not exists menus
(
    menu_type               varchar(10) not null,
    menu_id                 bigint      not null
        constraint menus_pk
            primary key,
    created_date            timestamp   not null,
    modified_date           timestamp   not null,
    menu_price              numeric,
    menu_name_fr            varchar(60),
    menu_desc_fr            text,
    menu_name_en            varchar(60),
    menu_desc_en            text,
    menu_nutritional_fr     text,
    menu_nutritional_en     text,
    menu_preparation_tip_fr text,
    menu_preparation_tip_en text,
    menu_category_id        bigint
        constraint menus_menu_category_fk
            references menu_category
);

create table if not exists orderlines
(
    order_line_id bigint  not null,
    amount        numeric not null,
    order_id      bigint  not null
        constraint orderlines_orders_fk
            references orders,
    menu_id       bigint  not null
        constraint orderlines_menus_fk
            references menus,
    units         integer,
    constraint orderlines_pk
        primary key (menu_id, order_id)
);

create table if not exists menu_ingredients
(
    menu_id            bigint
        constraint menu_ingredients_menus_fk
            references menus,
    ingredient_name_fr varchar(50) not null,
    ingredient_key     integer,
    ingredient_name_en varchar(50)
);

create table if not exists menu_prices
(
    menu_price_id bigint    not null
        constraint menu_prices_pk
            primary key,
    date_from     timestamp not null,
    date_end      timestamp,
    menu_id       integer   not null
        constraint menu_prices_menus_fk
            references menus,
    menu_price    numeric   not null
);

create table if not exists menu_product
(
    menu_id    bigint not null
        constraint menu_product_menus_fk
            references menus,
    product_id bigint
        constraint menu_product_products_fk
            references products
);

create table if not exists menu_status_link
(
    menu_status_id   bigint      not null
        constraint menu_status_link_pk
            primary key,
    date_from        timestamp   not null,
    date_end         timestamp,
    menu_id          integer     not null
        constraint menu_status_link_menus_fk
            references menus,
    menu_status_code varchar(50) not null
);

create table if not exists menu_price_options
(
    menu_price_id              integer
        constraint menu_price_options_menu_price_fk
            references menu_prices,
    price                      numeric,
    menu_price_option_id       bigint  not null
        constraint menu_price_options_pk
            primary key,
    menu_price_option_quantity integer not null
);

create table if not exists countries
(
    nis_code varchar(10) not null
        constraint countries_pk
            primary key,
    name_fr  varchar(50),
    name_en  varchar(50)
);

create unique index if not exists countries_nis_code_uindex
    on countries (nis_code);

create table if not exists cities
(
    nis_code varchar(10) not null
        constraint cities_pk
            primary key,
    name_fr  varchar(50),
    name_en  varchar(50)
);

create table if not exists cities_districts
(
    district_code    varchar(5) not null
        constraint cities_districts_pk
            primary key,
    city_nis_code    varchar(5) not null
        constraint cities_districts_cities_fk
            references cities,
    district_desc_fr varchar(50),
    district_desc_en varchar(50),
    deliverable      boolean
);

create table if not exists menu_info
(
    menu_info_id      bigint      not null
        constraint menu_info_pk
            primary key,
    status            varchar(10) not null,
    menu_info_version bigint      not null,
    menu_id           bigint      not null
        constraint menu_info_menus_fk
            references menus,
    menu_info_type    varchar(20) not null,
    created_date      timestamp   not null,
    modified_date     timestamp   not null,
    file_id_in_cloud  varchar(50),
    file_provider     varchar(10),
    district_nis_code varchar(5)
        constraint menu_info_district_fk
            references cities_districts
);

