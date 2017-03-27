(ns datomic-tutorial.core
  (:require [datomic.api :as d]))

(def db-url "datomic:free://127.0.0.1:4334/datomic-tutorial")

(def schema [{:db/doc "A users email."
              :db/ident :user/email
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one
              :db.install/_attribute :db.part/db}

             {:db/doc "A users age."
              :db/ident :user/age
              :db/valueType :db.type/long
              :db/cardinality :db.cardinality/one
              :db.install/_attribute :db.part/db}])

(def test-data
  [{:user/email "sally.jones@gmail.com"
    :user/age 34}

   {:user/email "franklin.rosevelt@gmail.com"
    :user/age 14}])

(defn reload-dbs []
  (d/delete-database db-url)
  (d/create-database db-url)
  (d/transact (d/connect db-url) schema)
  (d/transact (d/connect db-url) test-data))

(defn query1 [db]
  (d/q '[:find ?e
         :where [?e :user/email]]
       db))

(query1 (-> db-url d/connect d/db))
